(ns lein-viz.sample)


(defn make-graph
  []
  {:service [:db :mailer]
   :db [:datasource]
   :datasource [:db-host :db-port :database :username :password]
   :mailer [:smtp-host :smtp-port]})


(defn make-tree
  []
  [:foo [10 20] :bar [30 40] :baz [50]])
