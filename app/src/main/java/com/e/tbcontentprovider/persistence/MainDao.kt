package com.e.tbcontentprovider.persistence

import android.database.Cursor
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.e.tbcontentprovider.model.ModelContent


@Dao
interface MainDao {

    @Query("SELECT * FROM tb_color")
    fun searchAll(): Cursor


    @Insert(onConflict = REPLACE)
    fun insert(modelContent: ModelContent): Long

    @Insert(onConflict = REPLACE)
    fun insert(list: List<ModelContent>)


    @Transaction
    fun insertOrUpdateSearch(
        pk: Int,
        color: String
    ) {

        val model = getModelContent(pk)

        if (model == null) {

            val listAll = getModelList()
            if (listAll.size > 10) {
                deleteModelByPk(listAll.get(10).pk)
            }
            insert(
                ModelContent(
                    pk,
                    color
                )
            )
        } else {
            updateModel(color, pk)
        }


    }

    @Query("select * from tb_color order by pk asc")
    fun getModelList(): List<ModelContent>

    @Query("select * from tb_color order by pk asc")
    fun getModelListCursor(): Cursor

    @Query("select * from tb_color where pk=:pk ")
    fun getModelContent(
        pk: Int
    ): ModelContent?

    @Query("update tb_color set color=:color where pk=:pk")
    fun updateModel(
        color: String,
        pk: Int
    )

    @Query("delete from tb_color where pk= :pk")
    fun deleteModelByPk(pk: Int)

    @Query("DELETE FROM tb_color WHERE pk =:pk")
    fun delete(pk: Int): Int

    @Update
    fun update(modelContent: ModelContent): Int
}