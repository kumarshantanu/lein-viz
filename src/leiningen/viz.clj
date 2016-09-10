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


(def payload-err
  "Expected visualization payload to have :graph-data or :tree-data key with non-nil value, but found keys %s")


(def graph-map-err
  "Expected visualization payload :graph-data to be a map, but found %s")


(def tree-coll-err
  "Expected visualization payload :tree-data to be a collection, but found %s")


(defn viz
  "Visualize graph and tree data.
  Run with --usage switch to see CLI options."
  [project & args]
  (let [{:keys [selector]
         :as cli-opts} (cli/parse-opts args)
        plugin-config  (proj/plugin-config project selector)
        payload-source (some :source [cli-opts plugin-config])
        {:keys [graph-data
                tree-data
                node-labels
                node-shapes
                seed-keys]
         :as payload}  (proj/resolve-payload project payload-source)
        output-file    (some :output-file  [cli-opts plugin-config])
        hide-missing?  (some :hide-missing [cli-opts plugin-config])
        zoom-node      (some :zoom-node    [cli-opts plugin-config])]
    (cond
      (not (or graph-data
             tree-data))         (main/abort (format payload-err   (pr-str (vec (keys payload)))))
      (and graph-data
        (not (map? graph-data))) (main/abort (format graph-map-err (pr-str graph-data)))
      (and tree-data
        (not (coll? tree-data))) (main/abort (format tree-coll-err (pr-str tree-data)))
      :otherwise            (viz/visualize {:graph graph-data
                                            :tree  tree-data
                                            :node-labels   node-labels
                                            :node-shapes   node-shapes
                                            :output-file   output-file
                                            :hide-missing? hide-missing?
                                            :known-missing (set seed-keys)
                                            :zoom-node zoom-node}))))
