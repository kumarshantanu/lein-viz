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


(defn plugin-config
  [project selector-key]
  (get-in project [project-key selector-key]))


(defn fetch-payload
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


(defn verify-payload
  [payload]
  (when-not (map? payload)
    (main/abort
      (format "Expected visualization payload to be a map, but found %s"
        (pr-str payload))))
  payload)


(defn resolve-payload
  [project payload-source]
  (if payload-source
    (cond
      (= :stdin payload-source)  (verify-payload (edn/read-string (slurp *in*)))
      (and
        (symbol? payload-source)
        (pos?                    ; is this a fully-qualified var name?
          (.indexOf
            (str payload-source)
            (int \/))))          (verify-payload (fetch-payload project payload-source))
      :otherwise                 (main/abort
                                   (format "Expected source to be ':stdin' or fully-qualified defn var name, found %s"
                                     (pr-str payload-source))))
    (main/abort
      (format "Must specify source via '--source' CLI option or via [%s selector :source] keypath in project.clj: %s"
        project-key (get project project-key)))))
