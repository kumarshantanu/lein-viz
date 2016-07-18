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
  (viz/view-graph (keys graph) graph
    :node->descriptor (fn [n] {:label n})))


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
