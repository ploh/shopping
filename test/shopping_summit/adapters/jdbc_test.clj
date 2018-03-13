(ns shopping-summit.adapters.jdbc-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [clojure.test.check
             [generators :as gen]
             [properties :as prop]
             [clojure-test :refer [defspec]]]
            [shopping-summit.use-cases.filling-cart :as filling-cart]
            [shopping-summit.adapters.jdbc :as jdbc-adapter]
            [shopping-summit.entities.cart :as cart]
            [clojure.java.jdbc :as jdbc]
            [com.stuartsierra.component :as component]
            [system.components.hikari :as hikari]))

#_
(defn with-db [callback]
  (let [db (-> (hikari/new-hikari-cp {:adapter "h2"
                                      :url "jdbc:h2:mem:test"})
               (component/start))]
    (try
      (jdbc/execute! db (jdbc-adapter/schema))
      (callback db)
      (finally
        (component/stop db)))))

#_
(deftest t-add-item
  (with-db
    (fn [db]
      (filling-cart/add-item-impl db "foo" {:name "milk"
                                            :quantity 67})
      (is (= (jdbc/query db "select * from items")
             [{:cart_id "foo"
               :name "milk"
               :quantity 67}])))))

#_
(deftest t-remove-item
  )

#_
(def gen-item-without-discount
  (gen/fmap (fn [item] (dissoc item :discount))
            (s/gen ::cart/item)))

#_
(defspec prop-every-item-being-inserted
  (prop/for-all [cart-id gen/string-alphanumeric
                 items (gen/list (s/gen ::cart/item))]
    (with-db
      (fn [db]
        (doseq [item items]
          (filling-cart/add-item-impl db cart-id item))
        (= (set (jdbc/query db ["select name, quantity from items
                                where cart_id = ?"
                                cart-id]))
           (set items))))))
