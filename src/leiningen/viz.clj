;   Copyright (c) Shantanu Kumar. All rights reserved.
;   The use and distribution terms for this software are covered by the
;   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
;   which can be found in the file LICENSE at the root of this distribution.
;   By using this software in any fashion, you are agreeing to be bound by
;   the terms of this license.
;   You must not remove this notice, or any other, from this software.


(ns leiningen.viz
  (:require
    [leiningen.viz.cli     :as cli]
    [leiningen.viz.core    :as viz]
    [leiningen.viz.project :as proj]))


(defn viz
  "Visualize graph and tree data."
  [project & args]
  (let [{:keys [target type]} (cli/parse-opts args)
        data (proj/resolve-data project target)
        type (proj/resolve-type project target data type)]
    (viz/visualize {:data data
                    :type type})))
