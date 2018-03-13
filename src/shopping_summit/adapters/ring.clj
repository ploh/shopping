(ns shopping-summit.adapters.ring
  (:require [ring.middleware.json :as json]
            bidi.ring
            [shopping-summit.use-cases.filling-cart :as filling-cart]))

#_
(defn post-to-cart [req]
  (let [item (get-in req [:body :item])
        cart-id (get-in req [:params :cart-id])]
    (filling-cart/add-item (::context req) cart-id item)
    {:status 201}))

#_
(defn not-found [req]
  {:status 404, :body "not found :(\n"})

#_
(defn routes []
  (bidi.ring/make-handler
    ["/" {["cart/" :cart-id] {:post post-to-cart}
          true not-found}]))

#_
(defn wrap-context [handler context]
  (fn [req]
    (handler (assoc req ::context context))))

#_
(defn make-handler [context]
  (-> (routes)
      (wrap-context context)
      (json/wrap-json-body {:keywords? true})
      (json/wrap-json-response {:pretty true})))
