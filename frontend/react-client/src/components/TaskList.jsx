function TaskList({ tasks, onTaskDeleted }) {
  const handleDelete = async (id) => {
    try {
      const response = await fetch(`http://localhost:8080/tasks?id=${id}`, {
        method: 'DELETE',
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Failed to delete');
      }

      onTaskDeleted(id);
    } catch (error) {
      console.error('Delete error:', error);
      alert("Failed to delete task.");
    }
  };

  return (
    <ul className="space-y-2">
      {tasks.map((task) => (
        <li
          key={task.id}
          className={`flex justify-between items-center p-3 rounded shadow-sm border ${
            task.priority === 'HIGH'
              ? 'bg-red-100 border-red-400'
              : task.priority === 'MEDIUM'
              ? 'bg-yellow-100 border-yellow-400'
              : 'bg-green-100 border-green-400'
          }`}
        >
          <span className="font-semibold">{task.title}</span>
          <div className="flex items-center gap-4">
            <span className="text-sm font-medium text-gray-700">{task.priority}</span>
            {task.dueDate && (
            <span className="text-xs text-gray-500 ml-2">
                📅 {task.dueDate}
            </span>
            )}
            <button
              onClick={() => handleDelete(task.id)}
              className="text-red-600 hover:text-red-800 text-sm"
            >
              ✕
            </button>
          </div>
        </li>
      ))}
    </ul>
  );
}

export default TaskList;
