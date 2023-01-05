package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.data.PostRepository

@Dao
interface PostDao {

    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)


    @Query("UPDATE posts SET content = :content WHERE id = :id")
    fun updateContentById(id: Long, content: String)


    fun save(post: PostEntity) =
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else updateContentById(
            post.id,
            post.content
        )

    @Query(
        """
        UPDATE posts SET
        likes = likes + CASE WHEN like_By_Me THEN -1 ELSE 1 END,
        like_By_Me = CASE WHEN like_By_Me THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun likeById(id: Long)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Long)
}