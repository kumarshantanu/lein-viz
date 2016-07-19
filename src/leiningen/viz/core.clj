;   Copyright (c) Shantanu Kumar. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file LICENSE at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.


(ns leiningen.viz.core
  (:require
    [rhizome.viz :as viz]))


(defn wait-for-window-close
  []
  (while (.isVisible (first @viz/default-frame))
    (Thread/sleep 10)))


(defn view-graph
  [graph]
  (let [all-dep-keys (set (mapcat second graph))
        missing-keys (set (filter #(not (contains? graph %)) all-dep-keys))]
    (viz/view-graph (concat (keys graph) missing-keys) graph
      :node->descriptor (fn [node] (let [color-leaf (fn [m]
                                                      (if (seq (get graph node))
                                                        m
                                                        (assoc m
                                                          :style :filled
                                                          :fillcolor :green)))
                                         color-root (fn [m]
                                                      (if (contains? all-dep-keys node)
                                                        m
                                                        (assoc m
                                                          :style :filled
                                                          :fillcolor :tan)))
                                         color-missing (fn [m]
                                                         (if (contains? missing-keys node)
                                                           (assoc m
                                                             :style :filled
                                                             :fillcolor :pink)
                                                           m))]
                                     (-> {:label node}
                                       color-leaf
                                       color-root
                                       color-missing))))))


(defn view-tree
  [tree]
  (viz/view-tree sequential? seq tree
    :node->descriptor identity))


(defn visualize
  [{:keys [data type] :or {type "graph"}}]
  (case type
    "graph" (view-graph data)
    "tree"  (view-tree data))
  (wait-for-window-close))
