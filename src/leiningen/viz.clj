;   Copyright (c) Shantanu Kumar. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file LICENSE at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.


(ns leiningen.viz
  (:require
    [leiningen.core.main   :as main]
    [leiningen.viz.cli     :as cli]
    [leiningen.viz.core    :as viz]
    [leiningen.viz.project :as proj]))


(defn viz
  "Visualize graph and tree data.
  Run with --usage switch to see CLI options."
  [project & args]
  (let [{:keys [selector]
         :as options}  (cli/parse-opts args)
        plugin-config  (proj/plugin-config project selector)
        payload-source (some :source [options plugin-config])
        {:keys [data]
         :as payload} (proj/resolve-payload project payload-source)
        data-type     (case (some :type [options plugin-config])
                        "graph" "graph"
                        "tree"  "tree"
                        (cond
                          (map? data) "graph"
                          (seq  data) "tree"
                          :otherwise  (main/abort
                                        (format "Cannot determine data type - expected a map or sequence, but found %s"
                                          (pr-str data)))))
        hide-missing? (some :hide-missing [options plugin-config])
        zoom-node     (some :zoom         [options plugin-config])]
    (viz/visualize {:data data
                    :type data-type
                    :hide-missing? hide-missing?
                    :zoom-node zoom-node})))
