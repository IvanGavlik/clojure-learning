(ns clojure-learning.data-structures.core)

(def names ["l p"])

(def basicMap {:fName "Marko" :lName "Polo"})

(def basicMap2 (hash-map :a 1 :b 2))

(get basicMap :fName)

(get basicMap2 :c "default value 3")

(def nestedMap {:a 1 :b {:x 2 :y 2}})

(get-in nestedMap [:b :x])

; Another way to look up a value in a map is to treat the map like a function with the key as its argument
(basicMap :fName)
(basicMap2 :c "default value 3.1")
; not working has to be like this (nestedMap [:b :x])
((nestedMap :b) :x)

;Create a map representing a student with keys :name, :age, and :grade. Add a new key :hobby with a value, and update the :grade to a new value.
; 1. create and manipulate maps
(def student {:name "Ivan" :age "30" :grade "A"})
(def studentWithHobby (assoc student :hobby "Clojure"))
(def studentGotB (assoc studentWithHobby :grade "B"))

;; 2. access map values
(def productPrice {:apple 1.2 :banana 0.5 :cherry 2.5})
(productPrice :banana)
(productPrice :orange 0)

;; 3. filter map
(def people {:Alice 30 :Bob 25 :Charlie 35})
(defn filter30Above [people]
  (filter (fn [map-item] (> (val map-item) 30)), people)
  )

;; merge maps
(def user {:id 123456 :fNmae "Ivan" :lName "Gavlik" :age 30 })
(def contact {:id 123456 :email "ivangavlik963@gmail.com" :tel "+385912266016"})
(def user-contact (merge user contact))

;; transformation
(def city-temp-celsius {:Berlin 20 :Paris 25 :Rome 30})
(defn cel-to-feh
  [[key val-cel]] [key (+ (* val-cel 2) 30)]
  )
(def city-temp-fahrenheit
  (into {} (map cel-to-feh city-temp-celsius))
  )

;; group
(def employers [ {:name "Alice" :department "IT" }
                { :name "Marko" :department "HR" }
                {:name "Pero" :department "HR"}
                ])

(def bySector (group-by :department employers))

;; remove by condition
(def fruit-stock-quantities {:apples 5 :orange 0 :pears 10})
(defn exist-fruit-stock? [[fruit stock]]
  (> stock 0)
  )
(defn fruit-stock-good [fruit-stock]
  (filter exist-fruit-stock? fruit-stock-quantities)
  )
(def fruit-stock-report (into {} (fruit-stock-good fruit-stock-quantities)))

;; nested map
(def school-class {
                   :class1 {:students ["Alice" "Bob"] :teacher "Ms. Smith" }
                   :class2 {:students ["Charlie" "Dana"] :teacher "Mr. Johnson"}
                   }
  )

(defn new-student-class2 [student school-class]
  (let [class2-students (get-in school-class [:class2 :students])
        add-new-student (conj class2-students student)
        class-new-student (assoc-in school-class [:class2 :students] add-new-student)
        ]
    class-new-student
    )
  )

;; count
(def letter-occurrence {:a 5 :b 10 :c 15})
(defn letter-occurrence-gt [n letter-occurrence]
  (let [filter-map (filter #(> (second %) n) letter-occurrence)
        ]
    (count filter-map)
    )
  ;;  (count (into [] (keys (filter #(> (second %) n)  letter-occurrence))))
  )

;; sort asc by value
(def item-price-data {:item1 20 :item2 10 :item3 30 :item4 10})
(defn sorted-item-price [item-price] (into (sorted-map-by (fn [k1 k2]
                                                            (compare [(get item-price k1) k1] [(get item-price k2) k2]))) item-price))


(comment
  (println "test " (sorted-item-price item-price-data)  )
  )