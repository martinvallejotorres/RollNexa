const base=import.meta.env.VITE_API_URL||'';
let csrf='';
async function ensureCsrf(){if(csrf)return;const r=await fetch(`${base}/api/auth/csrf`,{credentials:'include'});if(!r.ok)throw new Error('No se pudo iniciar la conexión segura');csrf=(await r.json()).token;}
export class ApiError extends Error{constructor(message:string,public code:string,public status:number){super(message)}}
export async function api<T>(path:string,options:RequestInit={},retryCsrf=true):Promise<T>{const method=(options.method||'GET').toUpperCase();const mutating=!['GET','HEAD','OPTIONS'].includes(method);if(mutating)await ensureCsrf();const r=await fetch(`${base}${path}`,{...options,credentials:'include',headers:{'Content-Type':'application/json',...(csrf?{'X-XSRF-TOKEN':csrf}:{}),...options.headers}});if(!r.ok){if(r.status===403&&mutating&&retryCsrf){csrf='';await ensureCsrf();return api<T>(path,options,false)}const body=await r.json().catch(()=>({message:'No pudimos completar la acción',error:'UNKNOWN'}));throw new ApiError(body.message||'Ocurrió un error',body.error||'UNKNOWN',r.status)}if(r.status===204)return undefined as T;return r.json()}
export const post=<T>(path:string,body?:unknown)=>api<T>(path,{method:'POST',body:body===undefined?undefined:JSON.stringify(body)});
export const put=<T>(path:string,body:unknown)=>api<T>(path,{method:'PUT',body:JSON.stringify(body)});
export const del=(path:string)=>api<void>(path,{method:'DELETE'});
