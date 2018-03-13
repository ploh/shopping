(ns shopping-summit.system
  (:require [com.stuartsierra.component :as component]
            [shopping-summit.adapters
             [ring :as ring]
             [jdbc :as jdbc]]
            [system.components
             [hikari :as hikari]
             [jetty :as jetty]]))

#_
(defrecord HTTP [port context jetty]

  component/Lifecycle

  (start [this]
    (let [handler (ring/make-handler context)
          jetty (jetty/new-jetty :port port
                                 :handler handler)]
      (assoc this :jetty (component/start jetty))))

  (stop [this]
    (component/stop (:jetty this))
    this))

#_
(defn system [{:keys [jdbc http]}]
  (component/system-map
    :jdbc (hikari/new-hikari-cp jdbc)
    :context (component/using {} {:filling-cart-impl :jdbc})
    :http (component/using (map->HTTP http) {:context :context})))
