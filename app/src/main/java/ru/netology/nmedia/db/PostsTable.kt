package ru.netology.nmedia.db

object PostsTable {

    const val NAME = "posts"

    val DDL = """
        CREATE TABLE $NAME(
        ${Column.ID.columnName} INTEGER PRIMARY KEY AUTOINCREMENT,
        ${Column.AUTHOR.columnName} TEXT NOT NULL,
        ${Column.PUBLISHED.columnName} TEXT NOT NULL,
        ${Column.CONTENT.columnName} TEXT NOT NULL,
        ${Column.LIKED_BY_ME.columnName} BOOLEAN NOT NULL DEFAULT 0,
        ${Column.LIKES.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.COUNT_LIKE.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.COUNT_SHARE.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.COUNT_EYE.columnName} INTEGER NOT NULL DEFAULT 0,
        ${Column.VIDEO_URL.columnName} TEXT NOT NULL

        
        );
        """.trimIndent()

    val ALL_COLUMNS_NAMES = Column.values().map { it.columnName }.toTypedArray()

    enum class Column (val columnName: String){
        ID("id"),
        AUTHOR("author"),
        PUBLISHED("published"),
        CONTENT("content"),
        LIKED_BY_ME("likedByMe"),
        LIKES("likes"),
        COUNT_LIKE ("count_like"),
        COUNT_SHARE("count_share"),
        COUNT_EYE("count_eye"),
        VIDEO_URL("video_url")

    }


}