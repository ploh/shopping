(ns shopping-summit.use-cases.filling-cart-test
  (:require [clojure.test :refer :all]
            [shopping-summit.use-cases.filling-cart
             :as filling-cart]))

(defrecord InMemory [store]

  filling-cart/AddItem
  (add-item-impl [this cart-id item]
    (swap! store update cart-id conj item))

  #_
  filling-cart/RemoveItem
  #_
  (remove-item-impl [this cart-id name]
    (let [delete (fn [items]
                   (remove (comp #{name} :name) items))]
      (swap! store update cart-id delete))))

(defn in-memory-carts [items]
  (->InMemory (atom items)))

(deftest t-add-item
  (let [in-memory (in-memory-carts {})
        context {:filling-cart-impl in-memory}
        item {:name "milk"
              :quantity 3}]
    (filling-cart/add-item context "foo" item)
    (is (= (get (deref (:store in-memory)) "foo")
           [item]))))

(deftest t-add-invalid-item
  (let [in-memory (in-memory-carts {})
        context {:filling-cart-impl in-memory}
        item {:name "milk"
              :quantity 0}]
    (is (thrown? clojure.lang.ExceptionInfo
                 (filling-cart/add-item context "foo" item)))))

(deftest t-add-item-with-invalid-cart-id
  (let [in-memory (in-memory-carts {})
        context {:filling-cart-impl in-memory}
        item {:name "milk"
              :quantity 3}]
    (is (thrown? AssertionError
                 (filling-cart/add-item context nil item)))))

#_
(deftest t-remove-item
  )

#_
(deftest t-remove-item-from-invalid-cart
  )
