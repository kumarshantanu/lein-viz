# lein-viz - History and TODO

## TODO

* CLI support
  * Main action `["-a" "--action ACTION" "Action: view|emit" :default "view" ..]`
  * Image type `["-e" "--ext EXTENSION" "Image file extension (file type)" :default "png" ..]`
  * Dependency graph `["-d" "--deps" "Treat graph data as dependency graph (for graph only)"]`
  * Read EDN input from STDIN when target is unspecified or when last arg is `--`


## 0.1.0 / 2016-July-??

* CLI support
  * Target (fully qualified var name, arity-0 fn) support for fetching visualization data
  * Type detection of visualization data
  * Help/usage request
* Project metadata support
  * All command line arg info can be specified via project config (under `:viz` key)
* Show graph GUI
* Show tree GUI
