package controller;

import model.Task;

import java.util.*;

/**
 *  История задач
 */
public class InMemoryHistoryManager implements HistoryManager {

    private Map<Integer, Node<Task>> customLinkedList = new HashMap<>();

    Node<Task> first;
    Node<Task> last;

    private static class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.prev = prev;
            this.next = next;
        }
    }

    void linkLast(Task task) {
        final Node<Task> lastNode = last;
        final Node<Task> newNode = new Node<>(lastNode, task, null);
        last = newNode;
        if (lastNode == null) {
            first = newNode;
        } else {
            lastNode.next = newNode;
            customLinkedList.put(task.getId(), newNode);
        }
    }

    public List<Task> getTasksForHistory() {
        List<Task> list = new ArrayList<>();
        Node<Task> node = first;
        while (node != null) {
            list.add(node.item);
            node = node.next;
        }
        System.out.println(list.size());
        return list;
    }

    private void removeNode(Node<Task> node) {
            if (node == first) {
                first = first.next;
                first.prev = null;
                return;
            } else if (node == last) {
                last = last.prev;
                last.next = null;
                return;
            } else {
                final Node<Task> prev = node.prev;
                final Node<Task> next = node.next;
                node.prev.next = next;
                node.next.prev = prev;
            }
    }


    @Override
    public List<Task> getHistory() {
        return getTasksForHistory();
    }


    @Override
    public void addToHistory(Task task) {

        final Node<Task> node = customLinkedList.get(task.getId());
        if (node != null) {
            removeFromHistory(task.getId());
            removeNode(node);
        }
        linkLast(task);

    }

    @Override
    public void removeFromHistory(int id) {
       Node node = customLinkedList.remove(id);
       if(node != null) {
           removeNode(node);
       }
    }
}