(ns browse-product.process
  (:require [browse-product.process-products :as process-products])
  (:require [browse-product.process-listings :as process-listings])
  (:require [clojure.string :as string])
  (:require [clojure.data.json :as json])
  (:require [clojure.java.io :as io]))

(defn match-listing
  [product-details-map listing-details-map]
  (if (and (not= nil (listing-details-map "title"))
    (not= nil (listing-details-map "manufacturer"))
    (= (product-details-map "manufacturer") 
       (listing-details-map "manufacturer"))
    (.contains (listing-details-map "title") 
      (product-details-map "manufacturer"))
    (and (not= nil (product-details-map "model"))
      (.contains (listing-details-map "title") 
        (product-details-map "model")))
    (or (= nil (product-details-map "family"))
       (.contains (listing-details-map "title")
         (product-details-map "family")))
    ) true false))

(defn extract-match-listing
  [product-details-map]
  (def product-listings-array (ref []))
  (with-open [listings-reader (io/reader "resources/listings.txt")]
    (doseq [listing-entry (line-seq listings-reader)]
      (let [listing-details-map (json/read-str listing-entry)]
        (if (match-listing product-details-map listing-details-map)
          (dosync (alter product-listings-array conj listing-entry))))))
  (println (str "{\"product_name\":\"" (product-details-map "product_name") "\",\"listings\":[" (string/join "," @product-listings-array) "]}")))

(defn extract-product-entry-match-listing 
  [product-entry]
  (let [product-details-map (json/read-str product-entry)] 
    (if (not= nil (product-details-map "product_name")) 
      (extract-match-listing product-details-map))))

(defn process-products-file
  "Reading the product file, get the details and process listings."
  []
  (with-open [products-reader (io/reader "resources/products.txt")]
    (doseq [product-entry (line-seq products-reader)]
      (extract-product-entry-match-listing product-entry))))
