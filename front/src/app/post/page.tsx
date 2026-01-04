"use client";

import { useState, useEffect } from "react";

export default function PostPage() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const fetchPosts = async () => {
      const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL;

      const postResp = await fetch(`${API_BASE_URL}/api/v1/posts`);
      const posts = await postResp.json();
      setPosts(posts);
    };

    fetchPosts();
  }, []);

  // console.log(posts);

  return (
    <div>
      <h1>글 목록</h1>
      <ul>
        {/* eslint-disable-next-line @typescript-eslint/no-explicit-any */}
        {posts.map((post: any) => (
          <li key={post.id}>{post.title}</li>
        ))}
      </ul>
    </div>
  );
}
