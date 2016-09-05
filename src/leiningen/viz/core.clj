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


(defn as-dep-keys
  "Given dependencies, return the dependency keys as a collection."
  [deps]
  (if (map? deps)
    (vals deps)   ; if deps are a map, then vals are the dependency nodes, keys are edge-names
    (into [] deps)))


(defn viz-graph
  "Given a graph with keys and either adjacency-list or map of adjacency-list to edge-labels, return a map of keys to
  adjacency-list."
  [graph]
  (let [ks (keys graph)
        vs (vals graph)]
    (zipmap ks (map as-dep-keys vs))))


(defn view-graph
  [graph {:keys [hide-missing? known-missing zoom-node]
          :or {hide-missing? true
               known-missing #{}}}]
  (let [graph (if zoom-node
                (letfn [(dep-keys  [node] (as-dep-keys (get graph node)))
                        (sub-graph [node] (loop [sub-ks [node]
                                                 rem-ks (dep-keys node)]
                                            (if (seq rem-ks)
                                              (recur (concat sub-ks rem-ks) (mapcat dep-keys rem-ks))
                                              (select-keys graph sub-ks))))]
                  (sub-graph zoom-node))
                graph)
        all-dep-keys (set (mapcat #(as-dep-keys (second %)) graph))
        missing-keys (set (filter #(not (contains? graph %)) all-dep-keys))]
    (viz/view-graph (concat (keys graph) (when-not hide-missing? missing-keys)) (viz-graph graph)
      :node->descriptor (fn [node] (letfn [(color-leaf    [m] (if (seq (get graph node))
                                                                m
                                                                (assoc m
                                                                  :style :filled
                                                                  :fillcolor :green)))
                                           (color-root    [m] (if (contains? all-dep-keys node)
                                                                m
                                                                (assoc m
                                                                  :style :filled
                                                                  :fillcolor :tan)))
                                           (color-missing [m] (if (contains? missing-keys node)
                                                                (assoc m
                                                                  :style :filled
                                                                  :fillcolor (if (contains? known-missing node)
                                                                               :orange
                                                                               :pink))
                                                                m))]
                                     (-> {:label node}
                                       color-leaf
                                       color-root
                                       color-missing)))
      ;; Labeled edges reference: http://zerosalife.github.io/blog/2014/04/26/clojure-rhizome-labeled-edge-tutorial/
      :edge->descriptor (fn [src dst] {:label (let [deps (get graph src)]
                                                (when (map? deps)
                                                  ;; In a dependency map the keys are edge-labels, so we reverse the
                                                  ;; map to lookup the destination in order to determine the edge label
                                                  (get (reduce (fn ([] {})
                                                                 ([m [k v]] (assoc m v k))) {} deps)
                                                    dst)))}))))


(defn view-tree
  [tree]
  (viz/view-tree sequential? seq tree
    :node->descriptor (fn [node] {:label (pr-str node)})))


(defn visualize
  [{:keys [graph
           tree
           hide-missing?
           known-missing
           zoom-node]}]
  (cond
    graph (view-graph graph {:hide-missing? hide-missing?
                             :known-missing known-missing
                             :zoom-node     zoom-node})
    tree  (view-tree tree))
  (wait-for-window-close))
