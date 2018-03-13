(ns shopping-summit.system-test
  (:require [shopping-summit.system :as system]
            [shopping-summit.adapters.jdbc :as jdbc-adapter]
            [clj-http.client :as http]
            [clojure.java.jdbc :as jdbc]
            [cheshire.core :as json]
            [clojure.test :refer :all]
            [com.stuartsierra.component :as component]))

(def http-port
  45183)

#_
(defn with-system [callback]
  (let [sys (-> (system/system {:jdbc {:adapter "h2"
                                       :url "jdbc:h2:mem:test"}
                                :http {:port http-port}})
                (component/start))]
    (try
      (jdbc/execute! (:jdbc sys) (jdbc-adapter/schema))
      (callback sys)
      (finally
        (component/stop sys)))))

#_
(deftest t-add-item
  (with-system
    (fn [{:keys [jdbc]}]
      (let [item {:name "tea", :quantity 1}]
        (http/post (format "http://localhost:%d/cart/abc" http-port)
                   {:body (json/generate-string {:item item})
                    :content-type :json})
        (is (= (jdbc/query jdbc ["select name, quantity from items
                                 where cart_id = 'abc'"])
               [item]))))))
