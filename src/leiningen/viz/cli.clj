;   Copyright (c) Shantanu Kumar. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file LICENSE at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.


(ns leiningen.viz.cli
  (:require
    [clojure.edn       :as edn]
    [clojure.string    :as string]
    [clojure.tools.cli :as cli]
    [leiningen.core.main :as main]))


(def cli-options
  [["-e" "--selector SELECTOR" "Selector key in :viz map in project.clj" :parse-fn edn/read-string
    :default :default]
   ["-s" "--source SOURCE" "Fully qualified var name (arity-0 fn)"       :parse-fn edn/read-string]
   ["-y" "--type DATATYPE" "Data type: Either of auto, graph and tree"
    :validate [#{nil "auto" "graph" "tree"} "Value must be auto, graph or tree"]]
   ["-u" "--usage" "Show usage text"]])


(defn usage
  "Return help text for this plugin."
  [options-summary]
  (format "
Visualize graph and tree data.

Usage: lein viz [options]

Options:
%s"
    options-summary))


(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
    (string/join \newline errors)))


(defn parse-opts
  "Parse command-line args and return options as a map."
  [args]
  (let [{:keys [options
                arguments
                errors
                summary]} (cli/parse-opts args cli-options)]
    (cond
      (:usage options) (main/abort (usage summary))
      errors           (main/abort  (error-msg errors)))
    options))
