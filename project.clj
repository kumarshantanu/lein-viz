(defproject lein-viz "0.2.0-SNAPSHOT"
  :description "Leiningen plugin to visualize graph and tree data"
  :url "https://github.com/kumarshantanu/lein-viz"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :eval-in-leiningen true
  :dependencies [[rhizome "0.2.7"]
                 [org.clojure/tools.cli "0.3.5"]]
  :viz {:graph lein-viz.sample/make-graph
        :tree  lein-viz.sample/make-tree})
