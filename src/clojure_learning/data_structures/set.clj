(ns clojure-learning.data-structures.set
  (:use clojure.set)
  (:require [clojure.string :as string]))


(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


(def compositions
  #{
    {:name "The Art of the Fugue" :composer "J. S. Bach"}
    {:name "Musical Offering" :composer "J. S. Bach"}
    {:name "Requiem" :composer "Giuseppe Verdi"}
    {:name "Requiem" :composer "W. A. Mozart"}})

(def composers
  #{
    {:composer "J. S. Bach" :country "Germany"}
    {:composer "W. A. Mozart" :country "Austria"}
    {:composer "Giuseppe Verdi" :country "Italy"}})


(def nations
  #{
    {:nation "Germany" :language "German"}
    {:nation "Austria" :language "German"}
    {:nation "Italy" :language "Italian"}})



(defn my-select []
  (let [selected (select #(= (:name %) "Requiem") compositions)]
    (println selected)))


(def simple-map {:sundance "s" :darwin "beagle"})

; Create a set of unique numbers from 1 to 10. Add 11 and remove 5 from the set.
(def numbers1-10 (hash-set 1 2 3 4 5 6 7 8 9 10))
(conj numbers1-10 11)
(disj numbers1-10 5)

; Write a function that checks whether a given element exists in a set.
(contains? numbers1-10 11)

; Given two sets, return a new set containing only the elements that are present in both sets.
(def numbers10-15 (hash-set 10 11 12 13 14 15))
(intersection numbers1-10 numbers10-15)

;; Write a function that takes a list of sets and returns their union.
(union numbers1-10 numbers10-15)

;; Given two sets, return a new set containing the elements
;; that are only in the first set but not in the second.
(difference numbers1-10 numbers10-15)

(def num-vector (vector 1 1 2 3 4 4 5))
(def num-set1-5 (into #{} num-vector))

;; Write a function that takes a string of words and returns
;; a set containing all unique words in lowercase.
(defn return-unique [words]
    (into #{} (string/split words #" ")))


;; Managing User Permissions with Sets
; user {:user-name :email :permission}
; permissions :read :write :delete
(defn valid-user-permission? [permission]
  (subset? permission #{:read :write :delete}))

(defn valid-user? [user]
  (and (some? (:user-name user))
       (some? (:email user))))

; Create a function to assign a set of permissions to a user.
(defn assign-user-permissions [user permissions]
  (when (and (valid-user? user) (valid-user-permission? permissions))
    (assoc user :permissions permissions)))

; Write a function to check if a user has a specific permission.
(defn has-user-permission? [user permission]
  (when (and (valid-user? user) (valid-user-permission? #{permission}))
    (contains? (:permissions user) permission)))

; Write a function to revoke a specific permission from a user.
(defn revoke-user-permissions [user permission]
  (when (and (valid-user? user) (valid-user-permission? #{permission}))
    (let [current-p (:permissions user)
          remove-p (disj current-p permission)]
      (assoc user :permissions remove-p))))


; Find users who have at least one common permission from a given permission set
(defn find-users-common-permissions [users permissions]
  (filter #(subset? permissions (:permissions %)) users))

; Find users who have all required permissions from a given permission set.
(defn find-users-all-permissions [users permissions]
  (filter #(= permissions (:permissions %)) users))

; Compute the difference between two users' permissions
(defn diff-users-permissions [user-1 user-2]
  (difference (:permissions user-1) (:permissions user-2)))

; Generate a report of all users and their permissions
; Example Alice -> #{:read :write}
(defn users-permissions-report [users]
  (map #(let [user (:user-name %)
              permissions (:permissions %)]
          (cond
            (some? permissions) (str user " -> " permissions)
            :else (str user)))
       users))


; TODO sets operations like projections union join ...

(defn main []
  (project
    (join
      (select #(= (:composer %) "J. S. Bach") compositions)
      composers)
    [:name :country]))


(comment
  (main))
