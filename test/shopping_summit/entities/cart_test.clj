(ns shopping-summit.entities.cart-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [shopping-summit.entities.cart :as cart]))

(deftest t-valid-cart
  (is (s/valid? (s/coll-of ::cart/item)
                [{:name "jam"
                  :quantity 3}
                 {:name "honey"
                  :quantity 2}])))

(deftest t-invalid-cart
  (is (not (s/valid? ::cart/item {})))
  (are [item] (not (s/valid? ::cart/item item))
       {:name "honey"}
       {:quantity 3}
       {:name ""
        :quantity 2}
       {:name "jam"
        :quantity 0}))

#_
(deftest t-discounted-cart
  )

#_
(deftest t-invalid-discounted-cart
  )
