(ns browse-product.process-products)

(def product-pattern "\\{[ ]*\"product_name\"[ ]*:[ ]*\"(.*)\"[ ]*,[ ]*\"manufacturer\"[ ]*:[ ]*\"(.*)\"[ ]*,[ ]*\"model\"[ ]*:[ ]*\"(.*)\"[ ]*,[ ]*\"family\"[ ]*:[ ]*\"(.*)\"[ ]*,[ ]*\"announced-date\"[ ]*:[ ]*\"[1-2][0-9]{3}-[0-1][0-9]-[0-3][0-9]T[0-2][0-9]:[0-5][0-9]:[0-5][0-9]\\.[0-9]{3}-[0-1][0-9]:[0-3][0-9][ ]*\"[ ]*\\}")

(defn skip-bad-product-entry
  [bad-entry]
  (println (str "Skipping invalid product entry:\n" bad-entry "...")))

(defn extract-family
  [product-entry]
  (nth (re-matches (re-pattern product-pattern) product-entry) 4 "")) 

(defn extract-model
  [product-entry]
  (nth (re-matches (re-pattern product-pattern) product-entry) 3 "")) 

(defn extract-manufacturer
  [product-entry]
  (nth (re-matches (re-pattern product-pattern) product-entry) 2 "")) 

(defn extract-product-name
  [product-entry]
  (nth (re-matches (re-pattern product-pattern) product-entry) 1 "")) 

(defn valid-product-entry
  [product-entry]
      (if (re-find (re-pattern product-pattern) product-entry) true false))
