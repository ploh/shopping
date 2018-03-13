(ns shopping-summit.main
  (:require [com.stuartsierra.component :as component]
            [shopping-summit.system :as system])
  (:gen-class))

(defn -main
  ([]
   (-main "8080"))
  ([port]
   (let [config {:jdbc {:adapter "h2"
                        :url "jdbc:h2:mem:test"}
                 :http {:port (Long/parseLong port)}}]
     (component/start (system/system config)))))
