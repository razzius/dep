(ns dep.core
  (:require [planck.http :refer [get]]))


(def package (first *command-line-args*))
(def url (str "https://clojars.org/api/artifacts/" package))

(def res (try
  (get url)
  (catch js/Error e
    (do
      (println "err" e)
      (throw e)))))

(def version (:latest_release (js->clj (.parse js/JSON (-> res :body)) :keywordize-keys true)))

(def deps (cljs.reader/read-string (planck.core/slurp "deps.edn")))

(def updated (assoc-in deps [:deps (symbol package)] {:mvn/version version}))

(planck.core/spit "deps.edn" (prn-str updated))
