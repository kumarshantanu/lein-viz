# lein-viz

A Leiningen plugin to visualize graph and tree data.


## Usage

Clojars: Not on Clojars yet

You need to have [Graphviz](http://www.graphviz.org/) installed to use this plugin.

| OS | How to install |
|----|----------------|
| Linux | Install using your package manager |
| OS X  | Install Graphviz using Homebrew, or [download installer](http://www.graphviz.org/Download_macos.php) |
| Windows | [Download installer](http://www.graphviz.org/Download_windows.php) |


### Installation

Use this for user-level installation:

Put `[lein-viz "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your `:user`
profile.

Use this for project-level installation:

Put `[lein-viz "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your project.clj.


### Quickstart

Create a function in your project to return visualization data.

```clojure
(ns  foo.core)

(defn make-graph
  []
  {:service [:db :mailer]
   :db [:datasource]
   :datasource [:db-host :db-port :database :username :password]
   :mailer [:smtp-host :smtp-port]
   :db-host [:config]
   :db-port [:config]
   :database [:config]})
```

Then run this plugin:

```
$ lein viz foo.core/make-graph
```

If you want to set the fetch fn as a default, put the following in `project.clj`:

```clojure
:viz {:default foo.core/make-graph}
```

Then, you can simply run:

```
$ lein viz
```


## License

Copyright © 2016 Shantanu Kumar (kumar.shantanu@gmail.com, shantanu.kumar@concur.com)

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
