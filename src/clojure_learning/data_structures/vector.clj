(ns clojure-learning.data-structures.vector)

(def numbers [3 2 1])
(def number2 (vector 4 3 2))

(get numbers 0)

; new vector with el on back
(conj numbers 0)

; revers
(rseq numbers)

; examine
(peek numbers)
(vector? numbers)
(vector? {:a 1})

; change
(subvec numbers 0 1)

(associative? numbers)

;; create and modify vector
(def numbers (vector 1 2 3 4 5))
(conj numbers 6)

;; retrieve the second element and last
;; handle case when index is out of bounds
(def fruit (vector "apple" "banana" "cherry" "date"))

(comment
  ; (vector (get data 2) (get data (- (count data) 1)))

  )
(defn second-last [data]
  (let [second (second data)
        last (last data)
        data (vector second last)
        ]
    (remove nil? data)
    ))