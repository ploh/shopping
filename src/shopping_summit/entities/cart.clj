(ns shopping-summit.entities.cart
  (:require [clojure.spec.alpha :as s]))

#_
(s/def ::item
  (s/keys :req-un [::name ::quantity]))

(s/def ::item
  (s/keys :req-un [::name ::quantity]
          :opt-un [::discount]))

(s/def ::name
  (s/and string? seq))

(s/def ::quantity
  (s/and int? pos?))

(s/def ::discount
  (s/and number?
         (fn [n] (< 0 n 1))))
