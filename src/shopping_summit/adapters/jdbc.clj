(ns shopping-summit.adapters.jdbc
  (:require [shopping-summit.use-cases.filling-cart
             :as filling-cart]
            [clojure.java.jdbc :as jdbc]
            [clojure.java.io :as io]
            system.components.hikari)
  (:import system.components.hikari.Hikari))

(extend-type Hikari
  filling-cart/AddItem
  (add-item-impl [this cart-id {:keys [name quantity]}]
    (jdbc/execute! this
                   ["insert into items
                    (cart_id, name, quantity) values (?,?,?)"
                    cart-id name quantity])))

(extend-type Hikari
  filling-cart/RemoveItem
  (remove-item-impl [this cart-id name]
    (jdbc/execute! this
                   ["delete from items
                    where name = ? and cart_id = ?"
                    name cart-id])))

(defn schema []
  (-> "shopping_summit/adapters/jdbc/schema.sql"
      (io/resource)
      slurp))
