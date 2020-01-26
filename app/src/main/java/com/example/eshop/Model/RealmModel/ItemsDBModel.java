//package com.example.eshop.Model.RealmModel;
//
//
//import java.util.List;
//
//import io.realm.RealmObject;
//
//public class ItemsDBModel extends RealmObject {
//
//    private List<Category> categories = null;
//
//    private List<Ranking> rankings = null;
//
//    public List<Category> getCategories() {
//        return categories;
//    }
//
//    public void setCategories(List<Category> categories) {
//        this.categories = categories;
//    }
//
//    public List<Ranking> getRankings() {
//        return rankings;
//    }
//
//    public void setRankings(List<Ranking> rankings) {
//        this.rankings = rankings;
//    }
//
//
//    public class Category  {
//
//
//        private Integer id;
//
//        private String name;
//
//        private List<Category.Product> products = null;
//
//        private List<Integer> childCategories = null;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public List<Category.Product> getProducts() {
//            return products;
//        }
//
//        public void setProducts(List<Category.Product> products) {
//            this.products = products;
//        }
//
//        public List<Integer> getChildCategories() {
//            return childCategories;
//        }
//
//        public void setChildCategories(List<Integer> childCategories) {
//            this.childCategories = childCategories;
//        }
//
//        public class Product {
//
//            private Integer id;
//
//            private String name;
//
//            private String dateAdded;
//
//            private List<Variant> variants = null;
//
//            private Tax tax;
//
//            public Integer getId() {
//                return id;
//            }
//
//            public void setId(Integer id) {
//                this.id = id;
//            }
//
//            public String getName() {
//                return name;
//            }
//
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            public String getDateAdded() {
//                return dateAdded;
//            }
//
//            public void setDateAdded(String dateAdded) {
//                this.dateAdded = dateAdded;
//            }
//
//            public List<Variant> getVariants() {
//                return variants;
//            }
//
//            public void setVariants(List<Variant> variants) {
//                this.variants = variants;
//            }
//
//            public Tax getTax() {
//                return tax;
//            }
//
//            public void setTax(Tax tax) {
//                this.tax = tax;
//            }
//
//        }
//
//
//    }
//
//
////-----------------------------------com.example.Product_.java-----------------------------------
//
//
//    public class Product_ {
//
//        private Integer id;
//
//        private Integer viewCount;
//
//        private Integer orderCount;
//
//        private Integer shares;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public Integer getViewCount() {
//            return viewCount;
//        }
//
//        public void setViewCount(Integer viewCount) {
//            this.viewCount = viewCount;
//        }
//
//        public Integer getOrderCount() {
//            return orderCount;
//        }
//
//        public void setOrderCount(Integer orderCount) {
//            this.orderCount = orderCount;
//        }
//
//        public Integer getShares() {
//            return shares;
//        }
//
//        public void setShares(Integer shares) {
//            this.shares = shares;
//        }
//
//    }
////-----------------------------------com.example.Ranking.java-----------------------------------
//
//
//
//    public class Ranking {
//
//        private String ranking;
//
//        private List<Product_> products = null;
//
//        public String getRanking() {
//            return ranking;
//        }
//
//        public void setRanking(String ranking) {
//            this.ranking = ranking;
//        }
//
//        public List<Product_> getProducts() {
//            return products;
//        }
//
//        public void setProducts(List<Product_> products) {
//            this.products = products;
//        }
//
//    }
////-----------------------------------com.example.Tax.java-----------------------------------
//
//
//    public class Tax {
//
//
//        private String name;
//
//        private double value;
//
//        public String getName() {
//            return name;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public double getValue() {
//            return value;
//        }
//
//        public void setValue(double value) {
//            this.value = value;
//        }
//
//    }
////-----------------------------------com.example.Variant.java-----------------------------------
//
//
//    public class Variant {
//
//
//        private Integer id;
//
//        private String color;
//
//        private Object size;
//
//        private Float price;
//
//        public Integer getId() {
//            return id;
//        }
//
//        public void setId(Integer id) {
//            this.id = id;
//        }
//
//        public String getColor() {
//            return color;
//        }
//
//        public void setColor(String color) {
//            this.color = color;
//        }
//
//        public Object getSize() {
//            return size;
//        }
//
//        public void setSize(Object size) {
//            this.size = size;
//        }
//
//        public Float getPrice() {
//            return price;
//        }
//
//        public void setPrice(Float price) {
//            this.price = price;
//        }
//
//    }
//
//}
////-----------------------------------com.example.Product.java-----------------------------------
//
//
