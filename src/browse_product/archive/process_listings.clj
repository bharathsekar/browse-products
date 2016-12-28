(ns browse-product.process-listings)

(def listing-pattern "\\{[ ]*\"title\"[ ]*:[ ]*\"(.*)\"[ ]*,[ ]*\"manufacturer\"[ ]*:[ ]*\"(.*)\"[ ]*,[ ]*\"currency\"[ ]*:[ ]*\".*\"[ ]*,[ ]*\"price\"[ ]*:[ ]*\".*\"[ ]*\\}")

(defn skip-bad-listing-entry
  [bad-listing-entry]
  (println "Skipping bad listing entry:\n" bad-listing-entry))

(defn extract-manufacturer
  [listing-entry]
  (nth (re-matches (re-pattern listing-pattern) listing-entry) 2 ""))

(defn extract-title
  [listing-entry]
  (nth (re-matches (re-pattern listing-pattern) listing-entry) 1 ""))

(defn valid-listing-entry
  [listing-entry]
  (if (re-find (re-pattern listing-pattern) listing-entry) true false))

(defn contained-in-title
  [listing-entry input]
  (.contains listing-entry input))

