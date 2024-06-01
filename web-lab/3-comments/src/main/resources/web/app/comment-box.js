import {LitElement, html, css} from 'lit';
import { unsafeHTML } from 'lit/directives/unsafe-html.js';

class CommentBox extends LitElement {

    static styles = css`
    :host {
        display: flex;
        flex-direction: column;
        overflow: hidden;
        padding: 5px;
        gap: 10px;
        font-family: -apple-system, BlinkMacSystemFont, 'Roboto', 'Segoe UI', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbol';
    }
    .comment-box {
        display: flex;
        flex-direction: column;
        gap:2px;
    }
    .existingcomment {
        border: 1px solid #C0C2CD;
        border-radius: 12px;
        padding: 10px;
    }

    .right {
        display: flex;
        flex-direction: row-reverse;
    }

    .commentByAndTime {
        display: flex;
        flex-direction: column;
    }

    .time {
        color: gray;
        font-size: small;
    }

    .input {
        width: 100%;
        padding: 12px 20px;
        margin: 8px 0;
        box-sizing: border-box;
        resize: none;
        border: 2px solid #ccc;
    }

    .button {
        background-color: #04AA6D; /* Green */
        border: none;
        color: white;
        padding: 15px 32px;
        text-align: center;
        text-decoration: none;
        display: inline-block;
        font-size: 16px;
        cursor: pointer;
    }

    markdown-toolbar {
        display: flex;
        gap: 10px;
    }

    fas-icon {
        color: gray;
        cursor: pointer;
    }
    fas-icon:hover {
        color: #212f80;
    }
`;

    constructor() {
        super();
        this.serverUrl = SERVER_URL;  // This is defined through web-bundler envs in application.properties
        this.ref = null;
        this._comments = [];
    }

    _fetchAllComments(){
        fetch(`${this.serverUrl}/comment/${this.ref}`)
            .then(response => response.json())
            .then(response => this._comments = response);
    }

    connectedCallback() {
        super.connectedCallback();
        this._fetchAllComments();
    }

    render() {
        return html`${this._renderNewComment()}
                ${this._renderExistingComments()}
      `;
    }


    _renderNewComment() {
        return html`
      <div class="comment-box">
        <input id="name" class="input" type="text" placeholder="Your name">
        <textarea id="comment" class="input" placeholder="Your comment. You can use Markdown for formatting" rows="5"></textarea>
        <button class="button" @click="${this._postComment}"><fas-icon icon="comment-dots" color="white"></fas-icon> Post Comment</button>
      </div>
    `;
    }

    _renderExistingComments(){
        if(this._comments){
            return html`${this._comments.map((comment) =>
                html`<div class="existingcomment">
                    <div class="comment">${comment.comment}</div>
                    <div class="right">
                        <div class="commentByAndTime">
                            by ${comment.name} on ${comment.time}
                        </div>
                    </div>
                </div>`
            )}
        `;
        }
    }

    _postComment() {
        let comment = new Object();
        comment.ref = this.ref;
        comment.name = this.shadowRoot.getElementById('name').value;
        comment.comment = this.shadowRoot.getElementById('comment').value;

        fetch(`${this.serverUrl}/comment`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(comment),
        })
            .then(response => response.json())
            .then(response => {
                    this._comments = response;
                    this._clear();
                }
            );
    }

    _clear(){
        // Let's make sure to reset the fields after adding a comment
        this.shadowRoot.getElementById('name').value = "";
        this.shadowRoot.getElementById('comment').value = "";
    }
}
customElements.define('comment-box', CommentBox);