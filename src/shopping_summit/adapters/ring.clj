(ns shopping-summit.adapters.ring
  (:require [ring.middleware.json :as json]
            bidi.ring
            [shopping-summit.use-cases.filling-cart :as filling-cart]))

(defn post-to-cart [req]
  (let [item (get-in req [:body :item])
        cart-id (get-in req [:params :cart-id])]
    (filling-cart/add-item (::context req) cart-id item)
    {:status 201}))

(defn delete-from-cart [req]
  (let [item (get-in req [:body :item])
        cart-id (get-in req [:params :cart-id])]
    (filling-cart/add-item (::context req) cart-id item)
    {:status 200}))

(defn not-found [req]
  {:status 404, :body "not found :(\n"})

(defn routes []
  (bidi.ring/make-handler
    ["/" {["cart/" :cart-id] {:post post-to-cart
                              :delete delete-from-cart}
          true not-found}]))

(defn wrap-context [handler context]
  (fn [req]
    (handler (assoc req ::context context))))

(defn make-handler [context]
  (-> (routes)
      (wrap-context context)
      (json/wrap-json-body {:keywords? true})
      (json/wrap-json-response {:pretty true})))
