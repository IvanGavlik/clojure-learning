(ns clojure-learning.control-flow.core)

(defn app []
  (if true "It is true" "It is false")

  (when true
    (println "Success!!")
    (println "User when do n things when true if false return null")
    )

  )

;; Both nil and false are used to represent logical0 falsiness,
;; whereas all other values are logically truthy
(defn app2 []
  (if "bla" (println "na  na  na  "))
  (println "clč 123")
  (if nil (println "I have nil and is true") (println "NIll is false"))
  )

(def severity :mild)
(def error-message "OH god It's disaster")
(defn error-handler [severity]
  (str "Oh my god! "

       (if (= severity :mild) "It is mildly"  "doomees")
       )
  )

; reduce
(reduce + (interpose 5 (map inc (range 10))))

(->> (range 10)
     (map inc)
     (interpose 5)
     (reduce +)
     )


(comment
  (do
    (println "Hello, World!")
    (println (app))
    (nil? 1)
    (app2)
    (println (or (= 0 1) (= "yes" "no")))  ; or returns either the first truthy value or the last value
    (println (or "this is true" (= "yes" "no")))  ; or returns either the first truthy value or the last value
    (println (or "this is true second time" (= "yes" "yes")))  ; or returns either the first truthy value or the last value
    (println (or (= 0 1) (= "yes" "yes") (= "yes" "no")))  ; or returns either the first truthy value or the last value
    (println (and "free_wifi" "hot_coffee")) ; and returns the first falsey value or, if no values are falsey, the last truthy value.
    (println (and "hot_coffee" "free_wifi" )) ; and returns the first falsey value or, if no values are falsey, the last truthy value.
    (println (and "hot_coffee" nil "free_wifi" )) ; and returns the first falsey value or, if no values are falsey, the last truthy value.
    (println (error-handler :mild))
    (println (error-handler :hard))
    )
  )