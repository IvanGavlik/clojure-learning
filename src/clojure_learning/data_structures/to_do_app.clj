(ns clojure-learning.data-structures.to-do-app
  (:require [clojure.core :as core]
            [clojure.string :as string])
  )

; Model

(def user #{
            {:id "1" :name "user name" :email "my email"}
            }
  )

(defn str-to-user [str] (
                          let [str-user (string/split str  #" ")]
                          (hash-map :id (nth str-user 0), :name (nth str-user 1) :email (nth str-user 2))
                          )
  )

(defn user-to-str [user] (str (:id user) " " (:name user) " " (:email user)))

(def project #{
               {:id "1" :name "todo"}
               }
  )

; priority -> Low  | Medium | High
; status -> TODO | In Progress | Completed
(def task #{
            {:is "1" :title "The" :description "J. S. " :priority "Low" :status "TODO" :due-date "2024-11-01" :assigned-id "assigned-user-id" :labels #{} :created "2024-09-29" :created-by "user-id" :project-id "1" :sub-task-id "2"}
            }
  )

; frequency-type -> minutes,  hour, day, week Example -> remind x minutes before
(def reminder #{
                {:id "1" :task-id "1" :frequency "1" :frequency-type "h"}
                })

; DB - write and read from file
(def location "/home/ivan/Documents/Projects/clojure-learning/resources/")

(defn read-db
  [name] (slurp (str (str location) name))
  )

(defn write-db
  [name, data] (spit (str (str location) name) data)
  )

; Persistence layer

(defn final-all [table] (read-db table))

(defn save [table, row]
  (let [current-data (final-all table)
        new-data (string/trim (str current-data "\n" row))
        ]
    (write-db table new-data)
    )
  )

(defn find-one [table, row] (
                              let [all (final-all table)
                                   all-list (string/split-lines all) ; one roe line
                                   all-match (filter #(= row %1) all-list)
                                   ]
                              (first all-match)
                              )
  )

(defn count [table] (
                      let [all (final-all table)
                           all-list (string/split-lines all) ; one roe line
                           ]
                      (core/count all-list)
                      ))

(defn delete [table, row] (
                            let [all (final-all table)
                                 all-list (string/split-lines all) ; one roe line
                                 all-rest-match (filter #(not( = row %1)) all-list)
                                 all-rest-match-item-str-new-line (apply str (map #(str %1 "\n") all-rest-match))
                                 ]
                            (write-db table, all-rest-match-item-str-new-line)
                            )
  )

(defn exist? [table, row] (some? (find-one table row)))

; TODO query methods



; ORM

(defn find-all-entity[entity mapper] (
                                       let [all (final-all entity)
                                            entity-per-line (string/split-lines all)
                                            mapped (map mapper entity-per-line)
                                            ]
                                       (set mapped)
                                       )
  )

(defn save-entity [entity-name entity entity-mapper] (
                                                       let [entity-to-string (entity-mapper entity)]
                                                       (save entity-name entity-to-string)
                                                       )
  )
; TODO others find-one, count, delete, exist

; service
;(defn save-user [user] (save-entity "user" user user-to-str) )
;(defn find-all-user (find-all-entity "user" str-to-user))