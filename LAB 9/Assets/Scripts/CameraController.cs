using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraController : MonoBehaviour
{

    public Transform cameraOrbit;
    public Transform target;

    public Rigidbody m_Rigidbody;

    public float m_Speed;

    void Start()
    {
        //offset = transform.position - player.transform.position;
        //m_Rigidbody = GetComponent<Rigidbody>();
        cameraOrbit.position = target.position;
    }

    // Update is called once per frame
    void Update()
    {
        //if (Input.GetMouseButton(0))
        //{
        //    float h = rotateSpeed * Input.GetAxis("Mouse X");
        //    float v = rotateSpeed * Input.GetAxis("Mouse Y");

        //    if (player.transform.eulerAngles.z + v <= 0.1f || player.transform.eulerAngles.z + v >= 179.9f)
        //        v = 0;

        //    player.transform.eulerAngles = new Vector3(player.transform.eulerAngles.x, player.transform.eulerAngles.y + h, player.transform.eulerAngles.z + v);
        //}

        //float scrollFactor = Input.GetAxis("Mouse ScrollWheel");

        //if (scrollFactor != 0)
        //{
        //    player.transform.localScale = player.transform.localScale * (1f - scrollFactor);
        //}
        transform.rotation = Quaternion.Euler(transform.rotation.x, transform.rotation.y, 0);
        transform.LookAt(target.position);

    }
    void FixedUpdate()
    {
        if (Input.GetKey(KeyCode.W))
        {
            //transform.position += transform.forward * speed;
            //m_Rigidbody.velocity = transform.forward * m_Speed;
            m_Rigidbody.AddForce(transform.forward * m_Speed);
        }
        if (Input.GetKey(KeyCode.S))
        {
            //transform.position -= transform.forward * speed;
            m_Rigidbody.AddForce(-transform.forward * m_Speed);
        }
        cameraOrbit.transform.position = target.transform.position;
    }
}
