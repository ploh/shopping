(ns shopping-summit.entities.cart-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [shopping-summit.entities.cart :as cart]))

(deftest t-valid-cart
  (is (s/valid? (s/coll-of ::cart/item)
                [{:name "jam"
                  :conf "microxhcg"
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

(deftest t-discounted-cart
  (is (s/valid? (s/coll-of ::cart/item)
                [{:name "jam"
                  :conf "microxhcg"
                  :quantity 3
                  :discount 0.4}
                 {:name "honey"
                  :quantity 2
                  :discount 0.1}
                  {:name "marmalade"
                  :quantity 3
                  :discount 0.3}])))

(deftest t-invalid-discounted-cart
  (is (not (s/valid? ::cart/item {})))
  (are [item] (not (s/valid? ::cart/item item))
       {:name "honey"
       :quantity 3
       :discount 0}
       {:quantity 3
        :name "jam"
        :discount 1}
       {:name "marmalade"
        :quantity 2
        :discount -5}
       {:name "marmite"
        :quantity 1
        :discount 5}))
