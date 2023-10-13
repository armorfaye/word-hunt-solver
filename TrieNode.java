import java.util.*;

public class TrieNode
{
    public Map<Character, TrieNode> children; 
    public boolean isWord; 

    public TrieNode(){
        children = new HashMap<Character, TrieNode>(); 
        isWord = false; 
    }

    public TrieNode add_child(Character c){
        TrieNode child = new TrieNode(); 
        children.put(c, child); 
        return child; 
    }

    public TrieNode get_child(Character c){
        return children.get(c); 
    }

    public TrieNode add_or_get_child(Character c){
        if (children.containsKey(c)){
            return children.get(c);
        }
        return add_child(c); 
    }

    public boolean has_child(Character c){
        return children.containsKey(c); 
    }
}