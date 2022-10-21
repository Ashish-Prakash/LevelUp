import java.util.Scanner;

public class LL {
    public static Scanner scn = new Scanner(System.in);

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public static ListNode reverse_rec(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode node = reverse_rec(head.next);
        head.next.next = head;
        head.next = null;
        return node;
    }

    public static ListNode reverse(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode curr = head;
        ListNode prev = null;
        while (curr != null) {
            ListNode forw = curr.next;
            curr.next = prev;
            prev = curr;
            curr = forw;
        }
        return prev;
    }

    public static ListNode midNode(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode slow = head;
        ListNode fast = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public static boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null)
            return true;

        ListNode mid = midNode(head);
        ListNode temp = mid.next;
        temp = reverse(temp);
        mid.next = null;
        ListNode p1 = head, p2 = temp;
        while (p1 != null && p2 != null) {
            if (p1.val != p2.val) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        temp = reverse(temp);
        mid.next = temp;
        return true;
    }

    public static void fold(ListNode head) {
        if (head == null || head.next == null)
            return;
        ListNode mid = midNode(head);
        ListNode temp = mid.next;
        temp = reverse(temp);
        mid.next = null;
        ListNode p1 = head, p2 = temp;
        while (p2 != null) {
            ListNode h1 = p1.next, h2 = p2.next;
            p1.next = p2;
            p2.next = h1;

            p1 = h1;
            p2 = h2;
        }
    }

    public static void unfold(ListNode head) {
        if (head == null || head.next == null)
            return;
        ListNode p1 = head, p2 = head.next;
        ListNode h1 = p1, h2 = p2;
        while (h2 != null && h2.next != null) {
            ListNode next = h2.next;
            h1.next = next;
            h2.next = next.next;

            h1 = h1.next;
            h2 = h2.next;
        }
        p2 = reverse(p2);
        h1.next = p2;
    }

    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy, p1 = l1, p2 = l2;
        while (p1 != null && p2 != null) {
            if (p1.val < p2.val) {
                temp.next = new ListNode(p1.val);
                p1 = p1.next;
            } else {
                temp.next = new ListNode(p2.val);
                p2 = p2.next;
            }
            temp = temp.next;
        }
        while (p1 != null) {
            temp.next = new ListNode(p1.val);
            p1 = p1.next;
            temp = temp.next;
        }
        while (p2 != null) {
            temp.next = new ListNode(p2.val);
            p2 = p2.next;
            temp = temp.next;
        }
        return dummy.next;
    }

    public static ListNode mergeSort(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode mid = midNode(head);
        ListNode next = mid.next;
        mid.next = null;

        return mergeTwoLists(mergeSort(head), mergeSort(next));
    }

    public static ListNode mergeKLists(ListNode[] lists) {
        if (lists.length == 0)
            return null;
        return mergeKLists(lists, 0, lists.length - 1);
    }

    public static ListNode mergeKLists(ListNode[] lists, int s, int e) {
        if (s == e) {
            return lists[s];
        }
        int mid = (s + e) / 2;
        ListNode left = mergeKLists(lists, s, mid);
        ListNode right = mergeKLists(lists, mid + 1, e);
        return mergeTwoLists(left, right);
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null)
            return head;
        ListNode fast = head, slow = head;
        while (n-- > 0) {
            fast = fast.next;
        }
        if (fast == null) {
            return head.next;
        }
        while (fast.next != null) {
            slow = slow.next;
            fast = fast.next;
        }
        ListNode rem = slow.next;
        slow.next = rem.next;
        rem.next = null;
        return head;
    }

    public static int length(ListNode head) {
        if (head == null)
            return 0;
        ListNode curr = head;
        int len = 0;
        while (curr != null) {
            curr = curr.next;
            len++;
        }
        return len;
    }

    private static ListNode th = null, tt = null;

    private static void addFirstNode(ListNode head) {
        if (th == null) {
            th = head;
            tt = head;
        } else {
            head.next = th;
            th = head;
        }
    }

    public static ListNode reverseInKGroup(ListNode head, int k) {
        if (head == null || head.next == null)
            return head;
        ListNode curr = head, oh = null, ot = null;
        int len = length(head);
        while (len >= k) {
            int tempK = k;
            while (curr != null && tempK-- > 0) {
                ListNode forw = curr.next;
                curr.next = null;
                addFirstNode(curr);
                curr = forw;
            }
            if (oh == null) {
                oh = th;
                ot = tt;
            } else {
                ot.next = th;
                ot = tt;
            }
            th = tt = null;
            len -= k;
        }
        ot.next = curr;
        return oh;
    }

    public static ListNode reverseInRange(ListNode head, int n, int m) {
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(-1), curr = head, prev = dummy;
        prev.next = head;
        int idx = 1;
        while (idx <= m) {
            while (idx >= n && idx <= m) {
                ListNode forw = curr.next;
                curr.next = null;
                addFirstNode(curr);
                curr = forw;
                idx++;
            }
            if (idx > m) {
                prev.next = th;
                tt.next = curr;
                break;
            }
            idx++;
            prev = curr;
            curr = curr.next;
        }

        return dummy.next;
    }

    public static ListNode removeDuplicates(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode curr = head.next, prev = head;
        while (curr != null) {
            while (curr != null && curr.val == prev.val) {
                ListNode forw = curr.next;
                curr.next = null;
                curr = forw;
            }
            prev.next = curr;
            prev = prev.next;
            if (curr != null) {
                curr = curr.next;
            }
        }
        return head;
    }

    public static ListNode removeAllDuplicates(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode dummy = new ListNode(-1);
        ListNode point = dummy;
        point.next = head;
        ListNode curr = head.next;
        while (curr != null) {
            boolean flag = false;
            while (curr != null && curr.val == point.next.val) {
                flag = true;
                curr = curr.next;
            }
            if (flag) {
                point.next = curr;
            } else {
                point = point.next;
            }
            if (curr != null) {
                curr = curr.next;
            }
        }
        return dummy.next;
    }

    public static boolean isCyclePresentInLL(ListNode head) {
        if (head == null || head.next == null)
            return false;
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    public static ListNode CycleNode(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }
        ListNode fast = head, slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                break;
            }
        }
        if (slow != fast) {
            return null;
        }
        slow = head;
        while (slow != fast) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    public static ListNode IntersectionNodeInTwoLL(ListNode headA, ListNode headB) {
        int s1 = getLength(headA);
        int s2 = getLength(headB);
        while (s1 > s2) {
            headA = headA.next;
            s1--;
        }
        while (s2 > s1) {
            headB = headB.next;
            s2--;
        }
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }
        return headA;
    }

    public static ListNode IntersectionNodeInTwoLL2(ListNode headA, ListNode headB) {
        ListNode a = headA, b = headB;
        while (a != b) {
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }
        return a;
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        int carry = 0;
        ListNode dummy = new ListNode(-1);
        ListNode itr = dummy;
        l1 = reverse(l1);
        l2 = reverse(l2);
        while (l1 != null || l2 != null || carry != 0) {
            int sum = 0;
            if (l1 != null) {
                sum += l1.val;
                l1 = l1.next;
            }
            if (l2 != null) {
                sum += l2.val;
                l2 = l2.next;
            }
            sum += carry;
            carry = sum / 10;
            sum = sum % 10;
            itr.next = new ListNode(sum);
            itr = itr.next;
        }
        return reverse(dummy.next);
    }

    public static int getLength(ListNode head) {
        if (head == null)
            return 0;
        ListNode curr = head;
        int len = 0;
        while (curr != null) {
            len++;
            curr = curr.next;
        }
        return len;
    }

    public static boolean isBigger(ListNode l1, ListNode l2) {
        while (l1 != null) {
            if (l1.val != 0) {
                break;
            }
            l1 = l1.next;
        }
        while (l2 != null) {
            if (l2.val != 0) {
                break;
            }
            l2 = l2.next;
        }
        int len1 = getLength(l1);
        int len2 = getLength(l2);
        if (len1 > len2) {
            return true;
        } else if (len1 < len2) {
            return false;
        }
        ListNode c1 = l1, c2 = l2;
        while (c1 != null) {
            if (c1.val > c2.val)
                return true;
            else if (c1.val < c2.val)
                return false;
            c1 = c1.next;
            c2 = c2.next;
        }
        return true;
    }

    public static ListNode subtractTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null || l2 == null) {
            return l1 == null ? l2 : l1;
        }
        ListNode c1 = null, c2 = null;
        if (isBigger(l1, l2)) {
            c1 = reverse(l1);
            c2 = reverse(l2);
        } else {
            c1 = reverse(l2);
            c2 = reverse(l1);
        }
        ListNode dummy = new ListNode(-1), itr = dummy;
        int borrow = 0;
        while (c1 != null || c2 != null) {
            int diff = borrow;
            if (c1 != null) {
                diff += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                diff -= c2.val;
                c2 = c2.next;
            }
            if (diff < 0) {
                borrow = -1;
                diff += 10;
            } else {
                borrow = 0;
            }
            itr.next = new ListNode(diff);
            itr = itr.next;
        }
        ListNode base = reverse(dummy.next), prev = dummy, curr = base;
        prev.next = null;
        while (curr != null) {
            if (curr.val != 0) {
                prev.next = curr;
                break;
            }
            ListNode forw = curr.next;
            curr.next = null;
            curr = forw;
        }
        return dummy.next == null ? new ListNode(0) : dummy.next;
    }

    public static ListNode multiplyTwoLL(ListNode l1, ListNode l2) {
        return null;
    }

    public static ListNode copyRandomList(ListNode head) {
        return null;
    }

    public static ListNode segregateEvenOdd(ListNode head) {
        if (head == null)
            return head;
        ListNode even = new ListNode(-1), odd = new ListNode(-1);
        ListNode p1 = even, p2 = odd;
        ListNode curr = head;
        while (curr != null) {
            if ((curr.val & 1) == 0) {
                p1.next = curr;
                p1 = p1.next;
            } else {
                p2.next = curr;
                p2 = p2.next;
            }
            curr = curr.next;
        }
        p1.next = odd.next;
        p2.next = null;
        return even.next;
    }

    public static ListNode segregate01(ListNode head) {
        if (head == null)
            return head;
        ListNode zero = new ListNode(-1), one = new ListNode(-1);
        ListNode p1 = zero, p2 = one, curr = head;
        while (curr != null) {
            if (curr.val == 0) {
                p1.next = curr;
                p1 = p1.next;
            } else {
                p2.next = curr;
                p2 = p2.next;
            }
            curr = curr.next;
        }
        p1.next = one.next;
        p2.next = null;
        return zero.next;
    }

    public static ListNode segregate012(ListNode head) {
        if (head == null)
            return head;
        ListNode zero = new ListNode(-1), one = new ListNode(-1), two = new ListNode(-1);
        ListNode p1 = zero, p2 = one, p3 = two;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == 0) {
                p1.next = curr;
                p1 = p1.next;
            } else if (curr.val == 1) {
                p2.next = curr;
                p2 = p2.next;
            } else {
                p3.next = curr;
                p3 = p3.next;
            }
            curr = curr.next;
        }
        p2.next = two.next;
        p1.next = one.next;
        p3.next = null;
        return zero.next;
    }

    public static void display(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }

    public static ListNode makeList(int n) {
        ListNode dummy = new ListNode(-1);
        ListNode temp = dummy;
        while (n-- > 0) {
            int val = scn.nextInt();
            temp.next = new ListNode(val);
            temp = temp.next;
        }
        ListNode head = dummy.next;
        return head;
    }

    public static void main(String[] args) {
        ListNode head = makeList(scn.nextInt());
        display(head);
    }
}
