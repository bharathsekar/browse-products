(ns main
  (:require [browse-product.process]))

(defn -main
  [& args]
  (browse-product.process/process-products-file))
