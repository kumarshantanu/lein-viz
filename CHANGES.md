# lein-viz - History and TODO

## TODO

* Payload
  * [TODO] Visualization metadata to be under `:layout`
* CLI support
  * [TODO] Main action `["-a" "--action ACTION" "Action: view|emit" :default "view" ..]`
  * [TODO] Image type `["-e" "--ext EXTENSION" "Image file extension (file type)" :default "png" ..]`


## 0.2.0 / 2016-September-04

* Payload
  * BREAKING CHANGE: Graph data to come under the key `:graph` in a payload map
  * BREAKING CHANGE: Tree data to come under the key `:tree` in a payload map
  * Known missing nodes to come under the key `:seed`
* Visualization
  * Support for labeled edges via dependency map of edge-labels to adjacency-list
* CLI support
  * BREAKING CHANGE: Target is now called selector: `["-e" "--selector SELECTOR" "Selector in project.clj"]`
  * Source may be specified via command-line: `["-s" "--source SOURCE" "Fully qualified defn var name (arity-0)"]`
  * BREAKING CHANGE: Source may now be placed only under the `:source` key under a selector
  * Read payload from STDIN when source is `:stdin`
  * Flag to show/hide missing nodes `["-m" "--hide-missing" "Hide missing nodes in a graph"]`
  * Flag to zoom in on a sub-graph at specified root `["-z" "--zoom-node NODE" "Sub-graph root node to zoom on"]`


## 0.1.0 / 2016-July-19

* CLI support
  * Target (fully qualified var name, arity-0 fn) support for fetching visualization data
  * Type detection of visualization data
  * Help/usage request
* Project metadata support
  * All command line arg info can be specified via project config (under `:viz` key)
* Show graph GUI
  * Color leaf nodes as green
  * Color root nodes as tan
  * Color missing dependency nodes as pink
* Show tree GUI
