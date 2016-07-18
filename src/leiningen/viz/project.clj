;   Copyright (c) Shantanu Kumar. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file LICENSE at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.


(ns leiningen.viz.project
  (:require
    [clojure.edn    :as edn]
    [clojure.string :as string]
    [leiningen.core.eval    :as eval]
    [leiningen.core.main    :as main])
  (:import
    [java.io File]))


(def project-key :viz)


(defn obtain-data
  [project fqvarname]
  (let [tokens (-> (str fqvarname)
                 (string/split #"/"))]
    (if (= 2 (count tokens))
      (let [ns-sym   (symbol (first tokens))
            fq-sym   (symbol (string/join "/" tokens))
            tmp-file (File/createTempFile "lein-viz-" ".edn")
            tmp-name (.getAbsolutePath tmp-file)]
        (.deleteOnExit tmp-file)
        (eval/eval-in-project project
          `(->> (~fq-sym)
             pr-str
             (spit ~tmp-name))
          `(require '~ns-sym))
        (edn/read-string (slurp tmp-file)))
      (main/abort
        (str "Expected fully-qualified var name (e.g. foo.bar/baz), but found " (pr-str fqvarname))))))


(defn resolve-data
  [project cli-target]
  (if (and (symbol? cli-target)
        (> (.indexOf (str cli-target) (int \/)) -1))  ; is this a fully-qualified var name?
    (obtain-data project cli-target)
    (if-let [target (get-in project [project-key cli-target])]
      (obtain-data project target)
      (main/abort
        (format "No such target %s found under the %s key in project.clj: %s"
          (pr-str cli-target) project-key (pr-str (get project project-key)))))))


(defn resolve-raw-type
  [data type]
  (if (= type "auto")
    (cond
      (map? data)        "graph"
      (sequential? data) "tree"
      :otherwise         (main/abort (format "Cannot determine data type - expected a map or sequence, but found %"
                                       (pr-str data))))
    type))


(defn resolve-type
  [project target data type]
  (if type
    (resolve-raw-type data type)
    (if-let [type (get-in project [project-key target :type])]
      (resolve-raw-type data type)
      (resolve-raw-type data "auto"))))
