import React, { useState } from 'react';

function TaskForm({ onTaskCreated }) {
  const [title, setTitle] = useState('');
  const [priority, setPriority] = useState('LOW');
  const [dueDate, setDueDate] = useState(''); // NEW

  const handleSubmit = async (e) => {
    e.preventDefault();
    const url = `http://localhost:8080/tasks?title=${encodeURIComponent(title)}&priority=${priority}&dueDate=${dueDate}`;
    const response = await fetch(url, { method: 'POST' });
    const newTask = await response.json();
    onTaskCreated(newTask);
    setTitle('');
    setPriority('LOW');
    setDueDate(''); // Reset field
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <input
        type="text"
        placeholder="Task title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
        required
        className="w-full p-2 border rounded"
      />
      <select
        value={priority}
        onChange={(e) => setPriority(e.target.value)}
        className="w-full p-2 border rounded"
      >
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>
      <input
        type="date"
        value={dueDate}
        onChange={(e) => setDueDate(e.target.value)}
        className="w-full p-2 border rounded"
      />
      <button
        type="submit"
        className="w-full bg-green-600 text-white p-2 rounded hover:bg-green-700"
      >
        Add Task
      </button>
    </form>
  );
}

export default TaskForm;
