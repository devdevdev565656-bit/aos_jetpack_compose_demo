package app.aos.jp.demo.other.navigation3scenestask

data class TodoItem(val id: Int, val title: String, val description: String)

val sampleTodos = listOf(
    TodoItem(1, "買牛奶", "去超市買全脂牛奶"),
    TodoItem(2, "寫報告", "完成 Q4 財務報告"),
    TodoItem(3, "健身", "跑步 5km")
)