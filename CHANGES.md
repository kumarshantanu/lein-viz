# lein-viz - History and TODO

## TODO

## 0.2.0 / 2016-September-??

* Payload
  * [TODO] Data to be under the key `:data`
  * [TODO] Visualization metadata to be under `:layout`
  * [TODO] Authorized seed keys to be under `:seed`
* Features
  * [TODO] Labeled edges: http://zerosalife.github.io/blog/2014/04/26/clojure-rhizome-labeled-edge-tutorial/
  * [TODO] Zoom into a subgraph by specifying the tip/root at the command line
* CLI support
  * [TODO] Flag to show/hide non-existent dependencies
  * [TODO] Flag to show the graph starting from a specified root
  * [TODO] Main action `["-a" "--action ACTION" "Action: view|emit" :default "view" ..]`
  * [TODO] Image type `["-e" "--ext EXTENSION" "Image file extension (file type)" :default "png" ..]`
  * [TODO] Dependency graph `["-d" "--deps" "Treat graph data as dependency graph (for graph only)"]`
  * [TODO] Read EDN input from STDIN when target is unspecified or when last arg is `--`


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
