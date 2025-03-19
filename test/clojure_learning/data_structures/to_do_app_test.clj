(ns clojure-learning.data-structures.to-do-app-test
  (:require [clojure.test :refer :all]
            [clojure-learning.data-structures.to-do-app :as todo :refer :all]
            [clojure.set :as set]
            ))

; DB test

(deftest read-db-test
  (testing "read a file have hard coded value test"
    (is (= "test" (read-db "test")))
    ))

(deftest write-db-test
  (testing "write to file test "
    (write-db "test-write" "test data")
    (is (= "test data"  (read-db "test-write") ))
    ))

; Persistence layer test

(deftest final-all-find-one
  (testing "read a db table test-persistence with one value test-persistence"
    (is (= "test-persistence" (final-all "test-persistence")))
    ))

(deftest final-all-table-not-exit
  (testing "if table not exit exception is thrown"
    (is (thrown? java.lang.Exception (final-all "test-persistence-not-exist")))
    ))


(deftest save-to-empty-test
  (testing "save to empty db "
    (write-db "test-table" nil)
    (save "test-table" "test data")
    (is (= "test data"  (final-all "test-table") ))
    ))

(deftest save-to-existing-test
  (testing "save to existing db expect to have two rws"
    (write-db "test-table-2" nil)
    (save "test-table-2" "test data")
    (save "test-table-2" "test data")
    (is (= "test data\ntest data"  (final-all "test-table-2") ))
    ))

(deftest find-one-test
  (testing "TODO desc"
    (write-db "test-table-3" nil)
    (save "test-table-3" "test data 1")
    (save "test-table-3" "test data 2")
    (is (= "test data 1" (find-one "test-table-3" "test data 1")))
    ))


(deftest find-one-duplicate-data-test
  (testing "return first"
    (write-db "test-table-4" nil)
    (save "test-table-4" "test data 1")
    (save "test-table-4" "test data 1")
    (save "test-table-4" "test data 2")
    (is (= "test data 1" (find-one "test-table-4" "test data 1")))
    ))

(deftest final-one-table-not-exit
  (testing "if table not exit exception is thrown"
    (is (thrown? java.lang.Exception (find-one "test-table-xy" "test data 1")))
    ))

(deftest count-test
  (testing "count 2"
    (write-db "test-table-5" nil)
    (save "test-table-5" "test data 1")
    (save "test-table-5" "test data 2")
    (is (= 2 (todo/count "test-table-5")))
    ))

(deftest count-table-not-exit
  (testing "if table not exit exception is thrown"
    (is (thrown? java.lang.Exception (todo/count "test-table-xy")))
    ))

(deftest delete-test
  (testing "delete first"
    (write-db "test-table-6" nil)
    (save "test-table-6" "test data 1")
    (save "test-table-6" "test data 2")
    (save "test-table-6" "test data 3")
    (delete "test-table-6", "test data 1")
    (is (= 2 (count "test-table-6")))
    (is (= nil (find-one "test-table-6", "test data 1")))
    ))

(deftest exist-test
  (testing "fist exist true second not exist so false"
    (write-db "test-table-7" nil)
    (save "test-table-7" "test data 1")
    (is (= true (exist? "test-table-7", "test data 1")))
    (is (= false (exist? "test-table-7", "test data 2")))
    ))

(deftest find-all-entity-test
  (testing "final all find one entity"
    (write-db "user" nil)
    (save "user" "1 marko123 test@mail.com")
    (is (= #{{:id "1" :name "marko123" :email "test@mail.com"}} (find-all-entity "user", str-to-user)))
    ))


(deftest find-all-entity-find-two-test
  (testing "final all find two entity"
    (write-db "user" nil)
    (save "user" "1 marko123 test@mail1.com")
    (save "user" "2 ivan123 test@mail2.com")
    (is (= #{
             {:id "1" :name "marko123" :email "test@mail1.com"}
             {:id "2" :name "ivan123" :email "test@mail2.com"}
             }
           (find-all-entity "user", str-to-user)))
    ))

(deftest find-all-entity-project-name
  (testing "final all find two entity"
    (write-db "user" nil)
    (save "user" "1 marko123 test@mail1.com")
    (save "user" "2 ivan123 test@mail2.com")
    (is (= #{
             {:name "marko123"}
             {:name "ivan123"}
             }
           (let [all (find-all-entity "user", str-to-user)]
             (set/project all [:name])
             )
           )
        )
    ))

(deftest save-entity-test
  (testing "save one entity"
    (write-db "user" nil)
    (save-entity "user", {:id "1", :name "xyz" :email "test@mail3.com"}, user-to-str)
    (is (= "1 xyz test@mail3.com", (find-one "user" "1 xyz test@mail3.com"))
        )))