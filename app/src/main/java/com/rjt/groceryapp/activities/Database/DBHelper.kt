package com.rjt.groceryapp.activities.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.rjt.groceryapp.models.Cart
import com.rjt.groceryapp.models.Product

class DBHelper(var mContext: Context) :
    SQLiteOpenHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION) {

    val db = this.writableDatabase

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "create table if not exists $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_PRODUCT_NAME char(50), $COLUMN_IMAGE char(200), $COLUMN_QTY INTEGER, $COLUMN_PRICE DOUBLE, $COLUMN_PID char(200))"

        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS ${DATABASE_NAME}"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

    fun addProductQty(product: Product, qty: Int) {

        // Get the current cart item with this product information

        if(!isProductInCart(product))

            // Adding Brand new product
            addToCart(product)
        else {

            // Updating existing product
            var contentValues = ContentValues()
            product.qty = product.qty + qty

            // inserting update value
            contentValues.put(COLUMN_QTY, product.qty + 1)
            db.update(TABLE_NAME, contentValues, "$COLUMN_PID = ?", arrayOf(product._id.toString()))
        }
    }

    fun addToCart(product: Product) {

        //store product info from product model to cart model
        val cart = Cart(
            cartId = 0,
            productName = product.productName,
            image = product.image,
            qty = 1,
            price = product.price,
            pid = product._id
        )

        val contentValues = ContentValues()

        contentValues.put(COLUMN_PRODUCT_NAME, cart.productName)
        contentValues.put(COLUMN_IMAGE, cart.image)
        contentValues.put(COLUMN_QTY, cart.qty)
        contentValues.put(COLUMN_PRICE, cart.price)
        contentValues.put(COLUMN_PID, cart.pid)

        db.insert(TABLE_NAME, null, contentValues)

    }

    fun updateProductQty(cart: Cart){
        val contentValues = ContentValues()

        contentValues.put(COLUMN_QTY, cart.qty)
        db.update(
            TABLE_NAME,
            contentValues,
            "$COLUMN_PID = ?",
            arrayOf(cart.pid)
        )
    }

    /**
     * Going through the entire database to check whether
     * the product exists insdie the database
     */
    fun isProductInCart(product: Product) : Boolean{
        var query = "Select * from $TABLE_NAME where $COLUMN_PID=?"
        var cursor = db.rawQuery(query, arrayOf(product._id))
        var count = cursor.count

        if(count == 0)
            return false
        else
            return true
    }

//    fun readProduct(id: String): Cursor {
//        val db = this.writableDatabase
//        var data: Cursor =
//            db.rawQuery("SELECT * from ${TABLE_NAME} WHERE ${COLUMN_ID} = $id", null)
//
//
//        return data
//    }

//    fun readAllProduct(): Cursor {
//        var db = this.writableDatabase
//        var data: Cursor =
//            db.rawQuery("SELECT * from $TABLE_NAME", null)
//
//        return data
//    }

    fun getAllCartProduct(): ArrayList<Cart> {

        var cartList: ArrayList<Cart> = ArrayList()
        var query = "select * from $TABLE_NAME"
        var columns = arrayOf(COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_IMAGE, COLUMN_QTY, COLUMN_PRICE, COLUMN_PID)
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst())
            do {
                var cart: Cart = Cart(0,"", "", 0, 0.0, "")
                cart.cartId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                cart.productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                cart.image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                cart.qty = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY))
                cart.price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                cart.pid = cursor.getString(cursor.getColumnIndex(COLUMN_PID))
                cartList.add(cart)

            }while (cursor.moveToNext())
        cursor.close()
        return cartList
    }


    fun getTotalPrice(): Double {

        var totalPrice = 0.0

        var cartList: ArrayList<Cart> = ArrayList()
        var query = "select * from $TABLE_NAME"
        var columns = arrayOf(COLUMN_ID, COLUMN_PRODUCT_NAME, COLUMN_IMAGE, COLUMN_QTY, COLUMN_PRICE, COLUMN_PID)
        var cursor = db.query(TABLE_NAME, columns, null, null, null, null, null)

        if (cursor != null && cursor.moveToFirst())
            do {
                var cart: Cart = Cart(0,"", "", 0, 0.0, "")
                cart.cartId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                cart.productName = cursor.getString(cursor.getColumnIndex(COLUMN_PRODUCT_NAME))
                cart.image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE))
                cart.qty = cursor.getInt(cursor.getColumnIndex(COLUMN_QTY))
                cart.price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
                cart.pid = cursor.getString(cursor.getColumnIndex(COLUMN_PID))
//                cartList.add(cart)

                totalPrice += cart.qty*cart.price
                Log.d("sam", ""+ totalPrice)

            }while (cursor.moveToNext())
        cursor.close()
        return totalPrice
    }

    fun deleteProduct(cart: Cart){
        val db = this.writableDatabase

        val WhereClause = "$COLUMN_ID = ?"
        val WhereArgs = arrayOf(cart.cartId.toString())
        db.delete(TABLE_NAME, WhereClause, WhereArgs)
    }



    companion object {
        val DATABASE_NAME = "productDB"
        val DATABASE_VERSION = 14
        val TABLE_NAME: String = "product"
        val COLUMN_ID: String = "cartId"
        val COLUMN_PRODUCT_NAME: String = "productName"
        val COLUMN_IMAGE: String = "image"
        val COLUMN_QTY: String = "qty"
        val COLUMN_PRICE: String = "price"
        val COLUMN_PID: String = "pid"
    }
}