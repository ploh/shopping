(ns shopping-summit.adapters.ring-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [clojure.test.check
             [generators :as gen]
             [properties :as prop]
             [clojure-test :refer [defspec]]]
            [ring.mock.request :as mock]
            [shopping-summit.use-cases.filling-cart :as filling-cart]
            [shopping-summit.adapters.ring :as ring]
            [shopping-summit.entities.cart :as cart]))

(defrecord InMemory [store]
  filling-cart/AddItem
  (add-item-impl [this cart-id item]
    (reset! store [:add cart-id item]))

  filling-cart/RemoveItem
  (remove-item-impl [this cart-id item]
    (reset! store [:remove cart-id item])))

(defn in-memory-carts []
  (->InMemory (atom nil)))

(deftest t-get-cart
  (let [req (mock/request :get "/cart/abc")
        handler (ring/make-handler {})
        resp (handler req)]
    (is (= (:status resp) 404))))

(deftest t-post-to-cart
  (let [item {:name "salt"
              :quantity 6}
        req (-> (mock/request :post "/cart/abc")
                (mock/json-body {:item item}))
        context {:filling-cart-impl (in-memory-carts)}
        handler (ring/make-handler context)
        resp (handler req)]
    (is (= (:status resp) 201))
    (is (= (deref (get-in context [:filling-cart-impl :store]))
           [:add "abc" item]))))

(deftest t-delete-from-cart
  (let [name "salt"
        req (-> (mock/request :delete "/cart/abc")
                (mock/json-body {:name name}))
        context {:filling-cart-impl (in-memory-carts)}
        handler (ring/make-handler context)
        resp (handler req)]
    (is (= (:status resp) 200))
    (is (= (deref (get-in context [:filling-cart-impl :store]))
           [:remove "abc" name]))))

#_
(def gen-simple-keyword
  (gen/fmap keyword gen/string-alphanumeric))

#_
(defspec prop-posting-items-does-not-500
  )
