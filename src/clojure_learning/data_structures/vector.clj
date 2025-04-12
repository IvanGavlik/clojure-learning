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

(comment)
  ; (vector (get data 2) (get data (- (count data) 1)))


(defn second-last [data]
  (let [second (second data)
        last (last data)
        data (vector second last)]

    (remove nil? data)))


;; update a vector
;; Modify a vector of student grades [85 90 78 92 88] by replacing the third grade with 80.
(def grades [85 90 78 92 88])
(def third-element 2)
(assoc grades third-element 80)

;; filter a vector
;; Write a function that takes a vector of numbers and returns a new vector containing only even numbers.
(def numbers-10 (vector 1 2 3 4 5 6 7 8 9 10))
(filter even? numbers-10)

; reverse
; Given a vector [10 20 30 40 50], return a new vector with elements in reverse order.
(def numbers-2 (vector 10 20 30 40 50))
(reverse numbers-2)

; merge two vectors
(def numbers-3 (vector 10 20 30 40 50))
(def numbers-4 (vector 10 20 30 40 50))
(reduce conj numbers-3 numbers-4)

; remove element at index
; Given a vector [:a :b :c :d :e], remove the element at index 2 (:c).
(def letters-keys (vector :a :b :c :d :e))
(remove #(= % (get letters-keys 2)) letters-keys)
(remove (fn [el] (= el (get letters-keys 2))) letters-keys)
(def value-to-remove (get letters-keys 2))
(remove #(= % value-to-remove) letters-keys)

; return the index of a given element in a vector
(def letters-keys-2 (vector :a :b :c :d :e))

(comment

  (.indexOf letters-keys-2 :g)
  (.indexOf nil :g)


  (let [index (.indexOf coll el)]
    (if (= -1 index) nil index)))



(defn index-of [coll el]
  (cond
    (nil? coll) nil
    :default (let [index (.indexOf coll el)]
               (if (= -1 index) nil index))))




; Write a function that takes a vector and a chunk size (n) and splits it into smaller vectors of length n.
(def letters-keys-2 (vector :a :b :c :d :e))

(def step 2)
(def chunk 2)
(mapv
  vec (partition chunk step (vector) letters-keys-2))


;; Sort asc a Vector of Maps by a Key
(def people [{:name "Bob" :age 25} {:name "Alice" :age 30} {:name "Charlie" :age 35}])
(sort people)

(defn sort-by-key [data sort-key]
  (sort (fn [e1 e2] (compare (sort-key e1) (sort-key e2)) ) people))


;; Managing a Task

;; Initialize Task List
(defn init-task-list []
  (hash-map {}))


(defn valid-priority [priority]
  (or (= :low priority) (= :medium priority) (= :high priority)))



(defn create-task [id title desc priority]
  (when (valid-priority priority)
    {:id id :title title :description desc :priority priority :completed? false}))



(defn task-not-exist? [task-list title]
  (= 0 (count (filter #(= (:title %)  title) task-list))))


;; Add a Task
(defn add-task [task-list title desc priority]
  (when (task-not-exist? task-list title)
    (let [last-task-id (:id (last task-list) 0)
          next-task-id (inc last-task-id)
          task (create-task next-task-id title desc priority)]
      (conj task-list task))))




;; Remove a Task
(defn remove-task [task-list task-id]
  (remove #(= (:id %) task-id) task-list))


;; Mark a Task as Completed
(defn mark-as-completed [task-list task-id]
  (let [task (first (filter #(= (% :id) task-id) task-list))
        index-of-task (index-of task-list task)]
    (when (not (nil? task))
      (assoc task-list index-of-task (assoc task :completed? true)))))




;; List All Pending Tasks
(defn pending-tasks [task-list]
  (filter #(not (:completed? %)) task-list))



;; Find Tasks by Priority
(defn filter-by-priority [task-list priority]
  (when (valid-priority priority)
    (filter #(= (:priority %) priority) task-list)))



(defn priority-count [priority]
  (cond
    (= priority :high) 3
    (= priority :medium) 2
    (= priority :low) 2
    :else 0))



(defn priority-higher-comparator [el1 el2] ()
  (let [priority1 (priority-count (el1 :priority))
        priority2 (priority-count (el2 :priority))]
    (compare priority2 priority1)))



;; Sort Tasks by Priority
(defn sort-by-priority [task-list]
  (sort priority-higher-comparator task-list))


;; Get Next Task
(defn next-task [task-list]
  (sort-by-priority (pending-tasks task-list)))
