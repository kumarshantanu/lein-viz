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
         :as cli-opts} (cli/parse-opts args)
        plugin-config  (proj/plugin-config project selector)
        payload-source (some :source [cli-opts plugin-config])
        {:keys [graph
                tree
                seed]
         :as payload}  (proj/resolve-payload project payload-source)
        hide-missing?  (some :hide-missing [cli-opts plugin-config])
        zoom-node      (some :zoom-node    [cli-opts plugin-config])]
    (viz/visualize {:graph graph
                    :tree  tree
                    :hide-missing? hide-missing?
                    :known-missing (set seed)
                    :zoom-node zoom-node})))
