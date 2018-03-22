(ns shopping-summit.use-cases.filling-cart
  (:require [shopping-summit.entities.cart :as cart]
            [clojure.spec.alpha :as s]))

(defprotocol AddItem
  (add-item-impl [this cart-id item]))

(defn add-item
  [context cart-id item]
  {:pre [(string? cart-id)]}
  (if (s/valid? ::cart/item item)
    (add-item-impl (:filling-cart-impl context)
                   cart-id item)
    (throw (ex-info "Invalid item"
                    (s/explain-data ::cart/item item)))))

(defprotocol RemoveItem
  (remove-item-impl [this cart-id name]))

(defn remove-item
  [context cart-id name]
  {:pre [(string? cart-id)]}
  {:pre [(string? name)]}
  (remove-item-impl (:filling-cart-impl context) cart-id name))
