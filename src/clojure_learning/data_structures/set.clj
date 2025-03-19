(ns clojure-learning.data-structures.set
  (:use clojure.set)
  )

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def compositions
  #{
    {:name "The Art of the Fugue" :composer "J. S. Bach"}
    {:name "Musical Offering" :composer "J. S. Bach"}
    {:name "Requiem" :composer "Giuseppe Verdi"}
    {:name "Requiem" :composer "W. A. Mozart"}
    }
  )

(def composers
  #{
    {:composer "J. S. Bach" :country "Germany"}
    {:composer "W. A. Mozart" :country "Austria"}
    {:composer "Giuseppe Verdi" :country "Italy"}
    }
  )

(def nations
  #{
    {:nation "Germany" :language "German"}
    {:nation "Austria" :language "German"}
    {:nation "Italy" :language "Italian"}
    }
  )


(defn my-select []
  (let [selected (select #(= (:name %) "Requiem") compositions)]
    (println selected)
    ))

(def simple-map {:sundance "s" :darwin "beagle"})


(defn main []
  (project
    (join
      (select #(= (:composer %) "J. S. Bach") compositions)
      composers)
    [:name :country])
  )

(comment
  (main)
  )